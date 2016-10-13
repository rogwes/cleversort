package nodomain.cleversort.random;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomIntegerService {

    private SecureRandom secureRandom = new SecureRandom();

    public int getRandomValueBelowGivenMax(int max) {
        return secureRandom.nextInt(max);
    }
}
