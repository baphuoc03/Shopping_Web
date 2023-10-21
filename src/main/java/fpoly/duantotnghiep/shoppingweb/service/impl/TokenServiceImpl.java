package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.Token;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenServiceImpl {

    private Map<String, Token> tokens = new HashMap<>();

    public void saveToken(String username,Token token){
            tokens.put(username,token);
        System.out.println(tokens.toString());
    }
    public Token getToken(String username){
        if(!tokens.containsKey(username)) return null;
        return tokens.get(username);
    }

}
