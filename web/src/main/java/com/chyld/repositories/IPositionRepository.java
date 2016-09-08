package com.chyld.repositories;


import com.chyld.entities.Position;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPositionRepository extends PagingAndSortingRepository<Position, Integer>{
}
