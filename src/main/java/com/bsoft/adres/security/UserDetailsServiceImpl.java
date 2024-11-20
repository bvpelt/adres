package com.bsoft.adres.security;

/*
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserDAO> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User: " + username + " not found");
            }
            return new MyUserPrincipal(user.get());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User: " + username + " not found");
        }
    }

}
*/