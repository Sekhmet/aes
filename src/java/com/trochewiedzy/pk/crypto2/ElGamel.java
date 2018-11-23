package com.trochewiedzy.pk.crypto2;

import java.util.Random;

public class ElGamel {
	
	public ElGamel() {
		
	}

	public BigNumber[] keyGenerator(){
		Mod mod = new Mod();
		BigNumber [] key = new BigNumber [4];
		BigNumber prime = mod.createPrime(1032, 100, new Random());
		BigNumber generator = mod.findPrimitiveRoot(prime);
		BigNumber index;
		do {
			index = new BigNumber(1024, new Random());
			
		}while(index.compareMagnitude(prime) >= 0);
		BigNumber A = mod.modPow(generator, index, prime);
		//System.out.println(prime);
		//System.out.println(index);
		key[0] = generator;
		key[1] = index;
		key[2] = prime;
		key[3] = A;
	
		
		return key;
	}
	
	public BigNumber [] encryption(BigNumber text, BigNumber [] key){
		BigNumber [] result = new BigNumber [2];
		BigNumber prime = key[1];
		prime = prime.subtract(BigNumber.ONE);
		BigNumber k, l;
		do {
		do {
			Random rand = new Random();
			k = new BigNumber(1024,rand);
			
		}while((k.compareMagnitude(prime)) >=0);
		Mod mod = new Mod();

		result[0] = mod.modPow(key[0], k, key[1]);
		result[1] = text.multiply(mod.modPow(key[2], k, key[1])).mod(key[1]);
		}while(result[0].toByteArray().length != 129 || result[1].toByteArray().length != 129 );
		return result;
	}
//	
//	public int decryption(int [] encryptText, int privateKey, int [] key) {
//		int decryptText;
//		int x = key[0] - 1 - privateKey;
//		
//		decryptText = new Mod().pmod(encryptText[0], x, key[0]);
//		int d = encryptText[1] % key[0];
//		decryptText = (decryptText * d) % key[0];
//		
//		return decryptText;
//	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
//	public int[] keyGenerator(int prime, int generator, int index) throws Exception {
//		int [] key = new int [3];
//		int A = new Mod().pmod(generator, index, prime);
//		key[0] = prime;
//		key[1] = generator;
//		key[2] = A;
//		
//		return key;
//	}
	
//	public int [] encryption(int text, int [] key,  int index) throws Exception {
//		int [] result = new int [2];
//		int b = index;
//		int B = new Mod().pmod(text, b, key[0]);
//		
//		int c = new Mod().pmod(key[2], b, key[0]);
//		int d = text % key[0];
//		c = (c * d) % key[0];
//		
//		result[0] = B;
//		result[1] = c;
//		
//		return result;
//	}
	
	public BigNumber decrypt(BigNumber [] result, BigNumber [] key) {
		Mod mod = new Mod();
		BigNumber c1 = mod.modPow(result[0], key[1], key[2]);
		c1 = mod.modInverse(c1, key[2]);
		BigNumber res = result[1].multiply(c1).mod(key[2]);
		return res;
	}
	
	
}
