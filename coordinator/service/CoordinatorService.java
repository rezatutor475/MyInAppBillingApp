package com.myinappbilling.coordinator.service;

import com.myinappbilling.coordinator.model.Coordinator;
import com.myinappbilling.coordinator.repository.CoordinatorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CoordinatorService {

    private final CoordinatorRepository repository;

    public CoordinatorService(CoordinatorRepository repository) {
        this.repository = repository;
    }

    public Coordinator createCoordinator(Coordinator coordinator) {
        coordinator.setCoordinatorId(UUID.randomUUID().toString());
        repository.save(coordinator);
        return coordinator;
    }

    public Optional<Coordinator> getCoordinatorById(String coordinatorId) {
        return repository.findById(coordinatorId);
    }

    public List<Coordinator> getAllCoordinators() {
        return repository.findAll();
    }

    public Coordinator updateCoordinator(Coordinator coordinator) {
        if (repository.existsById(coordinator.getCoordinatorId())) {
            repository.save(coordinator);
            return coordinator;
        }
        throw new IllegalArgumentException("Coordinator not found with ID: " + coordinator.getCoordinatorId());
    }

    public boolean deleteCoordinator(String coordinatorId) {
        if (repository.existsById(coordinatorId)) {
            repository.delete(coordinatorId);
            return true;
        }
        return false;
    }

    public List<Coordinator> findCoordinatorsByName(String name) {
        return repository.findByName(name);
    }

    public List<Coordinator> findCoordinatorsByRole(String roleId) {
        return repository.findByRoleId(roleId);
    }

    public List<Coordinator> searchCoordinators(String keyword) {
        return repository.search(keyword);
    }
}
