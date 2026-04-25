package ci.trabrouss.saas.services.impl;

import ci.trabrouss.saas.repositories.UserRepository;
import ci.trabrouss.saas.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouvé avec " + username));
  }


}
