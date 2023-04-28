package com.jungma.book.springboot.config.auth;

import com.jungma.book.springboot.config.auth.dto.OAuthAttributes;
import com.jungma.book.springboot.config.auth.dto.SessionUser;
import com.jungma.book.springboot.domain.user.User;
import com.jungma.book.springboot.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId : 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값
        // 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않는다.
        // 구글의 기본 코드는 "sub" 이다.
        // 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용된다.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuthAttributes : OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담을 클래스
        // 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용한다.
        // OAuthAttributes 에서는 다른 소셜 로그인도 이 클래스를 사용한다.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // User 클래스를 사용하지 않고 새로 만들어서 사용하는 이유는 User 클래스는 엔티티이기 때문에 다른 엔티티와 관계가 형성될 수 있다.
        // User 클래스의 직렬화 코드를 넣을 경우 다른 엔티티와 관계(OneToMany, ManyToOne 등)가 형성되면 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높다.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        // email 을 통해 이미 생성된 사용자인지, 처음 가입하는 사용자인지 판단한다.
        // 이미 생성된 사용자라면 update, 처음 가입하는 사용자라면 insert 를 한다.
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
