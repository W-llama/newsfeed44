package com.sparta.champions_league.repository;


import com.sparta.champions_league.entity.ApiUseTime;
import com.sparta.champions_league.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}