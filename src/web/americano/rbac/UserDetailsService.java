package americano.rbac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService
implements org.springframework.security.core.userdetails.UserDetailsService
{

	protected static List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(
		Arrays.asList( new SimpleGrantedAuthority( "ROLE_USER" ) )
	);
	
	public UserDetails loadUserByUsername( String username )
		throws UsernameNotFoundException
	{
		User user = new User( username, "aaa", authorities );
		return user;
	}

}
