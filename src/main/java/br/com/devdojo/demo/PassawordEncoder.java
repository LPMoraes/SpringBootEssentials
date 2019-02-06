package br.com.devdojo.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassawordEncoder {

    public static  void main(String[] args){

        BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("devdojo"));
    }


}
