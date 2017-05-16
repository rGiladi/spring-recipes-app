package com.roy.repositories;

import org.springframework.stereotype.Repository;

import com.roy.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
