package com.example.demo.utils;

import com.example.demo.dao.model.Member;
import com.example.demo.dao.repository.MemberRepository;
import com.example.demo.dto.MemberReceiver;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.MemberService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    MemberRepository repository;
    final Pattern patternType = Pattern.compile("[a-zA-Z]+");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ResourceNotFoundException {
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        String userId;
        try{
            /**
             * get id
             * remember that here token is id+type
             * for example:
             * 1usr means that user is command user and his id in command user is 1
             * 2support means that user is a company and his id in company is 2
             */
            Claims claims = JwtUtils.parseToken(token);
            userId = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Illegal token\n");
        }
        Matcher matcherType = patternType.matcher(userId);
        Integer id = Integer.parseInt(matcherType.replaceAll(""));
        Optional<Member> memberOptional = repository.findById(id);
        if(memberOptional.isEmpty()){
            throw new ResourceNotFoundException("No such account");
        }
        Member m = memberOptional.get();
        UsernamePasswordAuthenticationToken newToken =
                new UsernamePasswordAuthenticationToken(m,null, Arrays.asList("user").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(newToken);
        /**
         * go on
         */
        filterChain.doFilter(request,response);
    }
}