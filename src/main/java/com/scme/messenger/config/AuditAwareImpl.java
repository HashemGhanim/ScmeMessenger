package com.scme.messenger.config;

import java.util.Optional;

import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.mapper.UserMapper;
import com.scme.messenger.model.User;
import com.scme.messenger.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("auditAwareImpl")
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    private final IUserService iUserService;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            User user = UserMapper.convertDtoToUser(new User() , iUserService.getUser(authentication.getName()));
            return Optional.of(user.getName() + "," + user.getRole().toString());
        }
        return Optional.of("System");
    }

}
