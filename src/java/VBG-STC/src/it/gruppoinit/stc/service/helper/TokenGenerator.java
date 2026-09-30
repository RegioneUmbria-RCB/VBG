package it.gruppoinit.stc.service.helper;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    private Random rn = new Random();

    public String getToken() {
	return randomString(32, 32);
    }

    private int rand(int lo, int hi) {
	int n = hi - lo + 1;
	int i = rn.nextInt() % n;
	if (i < 0) {
	    i = -i;
	}
	return lo + i;
    }

    private String randomString(int lo, int hi) {
	int n = rand(lo, hi);
	byte b[] = new byte[n];
	for (int i = 0; i < n; i++) {
	    b[i] = (byte) rand('a', 'z');
	}
	return new String(b);
    }

}
