package com.axiomsl.hotel.service;

import com.axiomsl.hotel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;


@Repository
@Transactional
@Service("roomService")
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public void setRoomRepositoryRepository(RoomRepository roomRepositoryRepository) {
        this.roomRepository = roomRepositoryRepository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Room> findAllByCriteria(String number, RoomType type, RoomDirection direction) {
        //return roomRepository.findAllBySearchCriteria(city, number, type, direction);
    	return roomRepository.findAll(Specifications.where(getSpecification(number, type, direction)),
                new Sort(Sort.Direction.ASC, "number"));
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Room> findAllByCriteriaAndCity(String city, String name, String number, RoomType type, RoomDirection direction) {
        //return roomRepository.findAllByCriteriaAndCity(city, number, type, direction);
    	return roomRepository.findAll(Specifications.where(getSpecification2(city, name, number, type, direction)),
                new Sort(Sort.Direction.ASC, "number"));
    }

    @Override
    public Room findOne(Long id) {
        return roomRepository.findOne(id);
    }

    /* Generate criteria query */
    private Specification<Room> getSpecification(final String number, final RoomType type, final RoomDirection direction) {
        return new Specification<Room>() {
            @Override
            public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            	//root.fetch(Room_.reservations, JoinType.LEFT);
                Predicate predicate = criteriaBuilder.conjunction();  
                if (number != null && !number.isEmpty()) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.number),
                            number);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                if (type != null && !type.equals(RoomType.All)) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.type),
                            type);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                if (direction != null && !direction.equals(RoomDirection.ALL)) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.direction),
                            direction);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                return predicate;
            }
        };
    }
    
    /* Generate criteria query2 */
    private Specification<Room> getSpecification2(final String city, final String name, final String number, final RoomType type, final RoomDirection direction) {
        return new Specification<Room>() {
            @Override
            public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            	//root.fetch(Room_.reservations, JoinType.LEFT);
                Predicate predicate = criteriaBuilder.conjunction();  
                if (city != null && !city.isEmpty()) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.hotel).get(Hotel_.city),
                            city);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                if (name != null && !name.isEmpty()) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.hotel).get(Hotel_.name),
                    		name);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                if (number != null && !number.isEmpty()) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.number),
                            number);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                if (type != null && !type.equals(RoomType.All)) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.type),
                            type);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                if (direction != null && !direction.equals(RoomDirection.ALL)) {
                    Predicate p = criteriaBuilder.equal(root.get(Room_.direction),
                            direction);
                    predicate = criteriaBuilder.and(predicate, p);
                }
                return predicate;
            }
        };
    }

    @Override
    public Room findByNumber(String number) {
        return roomRepository.findByNumber(number);
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void delete(Long id) {
        roomRepository.delete(id);
    }
}
