package org.example.hospitalapp.component;

import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.entity.Adminstrator;
import org.example.hospitalapp.entity.Role;
import org.example.hospitalapp.entity.Room;
import org.example.hospitalapp.entity.User;
import org.example.hospitalapp.repo.AdminstratorRepository;
import org.example.hospitalapp.repo.RoleRepository;
import org.example.hospitalapp.repo.RoomRepository;
import org.example.hospitalapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final AdminstratorRepository adminstratorRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) {


        if (ddl.equals("create")) {
            Role roleSupperAdmin = new Role("ROLE_SUPPER_ADMIN");
            roleRepository.saveAll(new ArrayList<>(List.of(
                            roleSupperAdmin,
                            new Role("ROLE_PATIENT"),
                            new Role("ROLE_DOCTOR"),
                            new Role("ROLE_ADMIN")
                    ))
            );
            userRepository.save(new User(
                    "ADMIN",
                    "SUPPER",
                    "+998935961476",
                    passwordEncoder.encode("admin123"),
                    new ArrayList<>(List.of(roleSupperAdmin))
            ));

            Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
            User user = new User(
                    "adminstrator",
                    "adminstrator",
                    "1234567",
                    passwordEncoder.encode("1234567"),
                    new ArrayList<>(List.of(roleAdmin))
            );
            userRepository.save(user);
            adminstratorRepository.save(new Adminstrator(user));

            roomRepository.saveAll(List.of(
                    new Room(1, "A"),
                    new Room(2, "B"),
                    new Room(3, "C"),
                    new Room(4, "D"),
                    new Room(5, "E"),
                    new Room(6, "F")
            ));
        }
    }
}
