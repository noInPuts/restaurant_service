package cphbusiness.noinputs.main.service;

public interface JwtService {
    Long getUserIdFromJwtToken(String jwtToken);
}
