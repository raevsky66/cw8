package telran.java55.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java55.accounting.dao.UserAccountRepository;
import telran.java55.accounting.dto.RolesDto;
import telran.java55.accounting.dto.UserDto;
import telran.java55.accounting.dto.UserEditDto;
import telran.java55.accounting.dto.UserRegisterDto;
import telran.java55.accounting.model.UserAccount;
import telran.java55.post.dao.PostRepository;
import telran.java55.post.dto.PostDto;
import telran.java55.post.model.Post;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUser(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto updateUser(String login, UserEditDto userEditDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(String name, String newPassword) {
		// TODO Auto-generated method stub

	}

}
