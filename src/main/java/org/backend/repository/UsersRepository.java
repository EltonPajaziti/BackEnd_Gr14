    package org.backend.repository;

    import org.backend.model.Users;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface UsersRepository extends JpaRepository<Users, Long> {
        // Mund të shtosh metoda shtesë p.sh.:
        Users findByEmail(String email);
    }
