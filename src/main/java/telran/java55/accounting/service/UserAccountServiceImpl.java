package telran.java55.accounting.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java55.accounting.dao.UserAccountRepository;
import telran.java55.accounting.dto.RolesDto;
import telran.java55.accounting.dto.UserDto;
import telran.java55.accounting.dto.UserEditDto;
import telran.java55.accounting.dto.UserRegisterDto;
import telran.java55.accounting.dto.exception.UserNotFoundException;
import telran.java55.accounting.model.Role;
import telran.java55.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

	final UserAccountRepository userAccountRepository;
	final ModelMapper modelMapper;
	
	@Override
	public UserDto register(UserRegisterDto userRegisterDto) {
		UserAccount userAccount = new UserAccount(userRegisterDto.getLogin(), userRegisterDto.getPassword(),userRegisterDto.getFirstName(),userRegisterDto.getLastName());
		userAccount = userAccountRepository.save(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserDto removeUser(String login) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userAccountRepository.delete(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserDto getUser(String login) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserDto updateUser(String login, UserEditDto userEditDto) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userAccount.setFirstName(userEditDto.getFirstName());
		userAccount.setLastName(userEditDto.getLastName());
		userAccount = userAccountRepository.save(userAccount);
		
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
			
		try {
	        Role.valueOf(role.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        throw new UserNotFoundException("Role not found: " + role);
	    }

		
		if(isAddRole) {
			
			userAccount.addRole(role);
				
		}
		else {
			userAccount.removeRole(role);
			
		}

		userAccount = userAccountRepository.save(userAccount);
		Set<String> rolesAsString = userAccount.getRoles().stream()
	            .map(Role::name)
	            .collect(Collectors.toSet());
		System.out.println(rolesAsString);
		return  RolesDto.builder().login(login).roles(rolesAsString).build();
	}

	@Override
	public void changePassword(String name, String newPassword) {
		UserAccount userAccount = userAccountRepository.findById(name).orElseThrow(UserNotFoundException::new);
		userAccount.setPassword(newPassword);
		userAccountRepository.save(userAccount);
	}

}
