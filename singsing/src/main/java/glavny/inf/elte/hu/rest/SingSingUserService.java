package glavny.inf.elte.hu.rest;

import glavny.inf.elte.hu.data.User;
import glavny.inf.elte.hu.data.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;



public class SingSingUserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SingSingUserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        logger.info("Authenticating " + username);
        User user = userRepository.findById(username).get();
        logger.info("User data " + user.getPassword());
        return new SingSingUserPrincipal(user);
    }
}
