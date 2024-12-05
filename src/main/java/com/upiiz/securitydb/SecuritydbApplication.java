package com.upiiz.securitydb;

import com.upiiz.securitydb.entities.PermissionEntity;
import com.upiiz.securitydb.entities.RolEntity;
import com.upiiz.securitydb.entities.RoleEnum;
import com.upiiz.securitydb.entities.UserEntity;
import com.upiiz.securitydb.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SecuritydbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritydbApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args -> {
			// Crear permisos
			PermissionEntity createPermission = PermissionEntity
					.builder().name("CREATE").build();

			PermissionEntity deletePermission = PermissionEntity
					.builder().name("DELETE").build();

			PermissionEntity updatePermission = PermissionEntity
					.builder().name("UPDATE").build();

			PermissionEntity readPermission = PermissionEntity
					.builder().name("READ").build();

			PermissionEntity deployPermission = PermissionEntity
					.builder().name("DEPLOY").build();

			// Crear roles
			RolEntity adminRol = RolEntity.builder().roleEnum(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission, deletePermission, updatePermission, readPermission)).build();

			RolEntity guestRol = RolEntity.builder().roleEnum(RoleEnum.GUEST)
					.permissions(Set.of(readPermission)).build();

			RolEntity userRol = RolEntity.builder().roleEnum(RoleEnum.USER)
					.permissions(Set.of(readPermission, updatePermission)).build();

			RolEntity developerRol = RolEntity.builder().roleEnum(RoleEnum.DEVELOPER)
					.permissions(Set.of(deployPermission, createPermission, deletePermission, updatePermission, readPermission)).build();


			// Usuarios
			UserEntity juan = UserEntity.builder()
					.username("Juan")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.roles(Set.of(developerRol))
					.build();

			UserEntity jose = UserEntity.builder()
					.username("Jose")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.roles(Set.of(userRol))
					.build();

			UserEntity admin = UserEntity.builder()
					.username("Admin")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.roles(Set.of(adminRol))
					.build();

			UserEntity ana = UserEntity.builder()
					.username("Ana")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.roles(Set.of(guestRol))
					.build();

			UserEntity guest = UserEntity.builder()
					.username("Guest")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.credentialNoExpired(true)
					.roles(Set.of(guestRol))
					.build();

			// Guardar a los usuarios - Creamos el repositorio
			userRepository.saveAll(List.of(juan, jose, admin, guest, ana));
		};
	}
}
