package com.trochewiedzy.pk.crypto2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.lang.Object;

public class ModMath {
	
	public ModMath() {
		
	}
	private BigNumber smallPrime(int bitLength, int certainty, Random rnd) {
        int magLen = (bitLength + 31) >>> 5;
        int temp[] = new int[magLen];
        int highBit = 1 << ((bitLength+31) & 0x1f);  
        int highMask = (highBit << 1) - 1;  

        while(true) {
         
            for (int i=0; i<magLen; i++)
                temp[i] = rnd.nextInt();
            temp[0] = (temp[0] & highMask) | highBit;  
            if (bitLength > 2)
                temp[magLen-1] |= 1; 

            BigNumber p = new BigNumber(temp, 1);

          
            if (bitLength > 6) {
                long r = p.remainder(BigNumber.SMALL_PRIME_PRODUCT).longValue();
                if ((r%3==0)  || (r%5==0)  || (r%7==0)  || (r%11==0) ||
                    (r%13==0) || (r%17==0) || (r%19==0) || (r%23==0) ||
                    (r%29==0) || (r%31==0) || (r%37==0) || (r%41==0))
                    continue; // Candidate is composite; try another
            }

      
            if (bitLength < 4)
                return p;

            if (primeToCertainty(p,certainty, rnd))
                return p;
        }
    }


    private BigNumber largePrime(int bitLength, int certainty, Random rnd) {
        BigNumber p;
        p = new BigNumber(bitLength, rnd).setBit(bitLength-1);
        p.mag[p.mag.length-1] &= 0xfffffffe;
        int searchLen = (bitLength / 20) * 64;
        Bit searchSieve = new Bit(p, searchLen);
        BigNumber candidate = searchSieve.retrieve(p, certainty, rnd);

        while ((candidate == null) || (candidate.bitLength() != bitLength)) {
            p = p.add(BigNumber.valueOf(2*searchLen));
            if (p.bitLength() != bitLength)
                p = new BigNumber(bitLength, rnd).setBit(bitLength-1);
            p.mag[p.mag.length-1] &= 0xfffffffe;
            searchSieve = new Bit(p, searchLen);
            candidate = searchSieve.retrieve(p, certainty, rnd);
        }
        return candidate;
    }
	
	
    public BigNumber createPrime(int bitLength, int certainty, Random rnd) {
        BigNumber prime;
        if (bitLength < 2)
            throw new ArithmeticException("bitLength < 2");
        prime = (bitLength < 95 ? smallPrime(bitLength, certainty, rnd)
                                : largePrime(bitLength, certainty, rnd));
        return prime;
    }
	
//	public boolean isPrime(BigDecimal big) { 
//		if(big.compareTo(new BigDecimal("3")) == -1 || big.equals((big.remainder(new BigDecimal("2")))))
//			return big.compareTo(new BigDecimal("2"))==0 || big.compareTo(new BigDecimal("3"))==0;
//		BigDecimal divisor = new BigDecimal("3");
//		
//		BigDecimal zero = new BigDecimal("0");
//		while ((divisor.compareTo(big) == -1 || divisor.compareTo(big) == 0 ) && (big.remainder(divisor).compareTo(zero) != 0)) 
//	            divisor = divisor.add(new BigDecimal("2"));
//		
//		BigDecimal output = big.remainder(divisor);
//	    return !output.equals(new BigDecimal("0"));
//		
//	}
	
	public int gcd(int a, int b) {
	    int pom;
		while(b!=0){
			pom = b;
			b = a%b;
			a = pom;	
		}
		
	    return a;
	}
	
//	public int [] factor(int a) throws Exception {
//		
//		int [] array = new int[0];
//		int i, e;
//
//		if(isPrime(a)) throw new Exception("It is a prime number"); 
//		
//		i = 2;
//		e = (int)(Math.sqrt(a));
//		
//		while (i <= e) {
//			while ((a % i) == 0) {
//					a /= i;
//					e = (int)(Math.sqrt(a));
//					array = addToArray(array, i);
//			}
//			i++;
//		}
//		
//		if (a > 1) array = addToArray(array, a);
//		return array;
//	}
		
    private BigNumber modPow2(BigNumber a,BigNumber exponent, int p) {

        BigNumber result = a.valueOf(1);
        BigNumber baseToPow2 = a.mod2(p);
        int expOffset = 0;

        int limit = exponent.bitLength();

        if (a.testBit(0))
           limit = (p-1) < limit ? (p-1) : limit;

        while (expOffset < limit) {
            if (exponent.testBit(expOffset))
                result = result.multiply(baseToPow2).mod2(p);
            expOffset++;
            if (expOffset < limit)
                baseToPow2 = baseToPow2.square().mod2(p);
        }

        return result;
    }

	
	 public BigNumber modPow(BigNumber a, BigNumber exponent, BigNumber m) {
	
	        boolean invertResult;
	        if ((invertResult = (exponent.signum < 0)))
	            exponent = exponent.negate();

	        BigNumber base = (a.signum < 0 || a.compareTo(m) >= 0
	                           ? a.mod(m) : a);
	        BigNumber result;
	        if (m.testBit(0)) { 
	            result = base.oddModPow(exponent, m);
	        } else {

	  
	            int p = m.getLowestSetBit();

	            BigNumber m1 = m.shiftRight(p);  
	            BigNumber m2 = BigNumber.ONE.shiftLeft(p);
	            BigNumber base2 = (a.signum < 0 || a.compareTo(m1) >= 0
	                                ? a.mod(m1) : a);
	            BigNumber a1 = (m1.equals(BigNumber.ONE) ? BigNumber.ZERO :
	                             base2.oddModPow(exponent, m1));
	            BigNumber a2 = modPow2(base, exponent, p);
	            BigNumber y1 = m2.modInverse(m1);
	            BigNumber y2 = m1.modInverse(m2);

	            result = a1.multiply(m2).multiply(y1).add
	                     (a2.multiply(m1).multiply(y2)).mod(m);
	        }

	        return (invertResult ? result.modInverse(m) : result);
	    }
	 
	 
	  private boolean passesMillerRabin(BigNumber aa, int iterations, Random rnd) {
	        BigNumber thisMinusOne = aa.subtract(BigNumber.ONE);
	        BigNumber m = thisMinusOne;
	        int a = m.getLowestSetBit();
	        m = m.shiftRight(a);
	        if (rnd == null) {
	            rnd = BigNumber.getSecureRandom();
	        }
	        for (int i=0; i<iterations; i++) {
	            BigNumber b;
	            do {
	                b = new BigNumber(aa.bitLength(), rnd);
	            } while (b.compareTo(BigNumber.ONE) <= 0 || b.compareTo(aa) >= 0);

	            int j = 0;
	            BigNumber z = modPow(b, m, aa);
	            while(!((j==0 && z.equals(BigNumber.ONE)) || z.equals(thisMinusOne))) {
	                if (j>0 && z.equals(BigNumber.ONE) || ++j==a)
	                    return false;
	                z = modPow(z, BigNumber.TWO, aa);
	            }
	        }
	        return true;
	    }
	  
	   boolean primeToCertainty(BigNumber a,int certainty, Random random) {
	        int rounds = 0;
	        int n = (Math.min(certainty, Integer.MAX_VALUE-1)+1)/2;
	        int sizeInBits = a.bitLength();
	        if (sizeInBits < 100) {
	            rounds = 50;
	            rounds = n < rounds ? n : rounds;
	            return passesMillerRabin(a,rounds, random);
	        }

	        if (sizeInBits < 256) {
	            rounds = 27;
	        } else if (sizeInBits < 512) {
	            rounds = 15;
	        } else if (sizeInBits < 768) {
	            rounds = 8;
	        } else if (sizeInBits < 1024) {
	            rounds = 4;
	        } else {
	            rounds = 2;
	        }
	        rounds = n < rounds ? n : rounds;

	        return passesMillerRabin(a,rounds, random);
	    }
	
	public BigNumber findPrimitiveRoot(BigNumber prime) {

		// Find prime factors of p-1 once
		BigNumber n = prime.subtract(BigNumber.ONE);
		List<BigNumber> factors = findPrimeFactors(n);

		// Try to find the primitive root by starting at random number
		BigNumber g;
		do {
			g = new BigNumber(1025, new Random());
			
		}while(g.compareMagnitude(prime) >= 0);
		while (!checkPrimitiveRoot(g, prime, n, factors)) {
			g = g.add(BigNumber.ONE);
		}
		return g;
	}

	private boolean checkPrimitiveRoot(BigNumber g, BigNumber p, BigNumber n, List<BigNumber> factors) {
		// Run g^(n / "each factor) mod p
		// If the is 1 mod p then g is not a primitive root
		Iterator<BigNumber> i = factors.iterator();
		while (i.hasNext()) {
			if (modPow(g, n.divide(i.next()), p).equals(BigNumber.ONE)) {
				return false;
			}
		}
		return true;
	}
	private List<BigNumber> findPrimeFactors(BigNumber number) {
			BigNumber n = number;
			BigNumber i=BigNumber.valueOf(2);
			BigNumber limit=BigNumber.valueOf(10000);// speed hack! -> consequences ???
		   	List<BigNumber> factors = new ArrayList<BigNumber>();
		   	while (!n.equals(BigNumber.ONE)){
				while (n.mod(i).equals(BigNumber.ZERO)){
		        factors.add(i);
				n=n.divide(i);
				// System.out.println(i);
				// System.out.println(n);
				if(isPrime(n)){
					factors.add(n);// yes?
					return factors;
				}
		     	}
				i=i.add(BigNumber.ONE);
				if(i.equals(limit))return factors;// hack! -> consequences ???
				// System.out.print(i+"    \r");
			}
				System.out.println(factors);
		return factors;
	}
	
	boolean isPrime(BigNumber r){
		return miller_rabin(r);
		// return BN_is_prime_fasttest_ex(r,bitLength)==1;
	}
	
	
	private boolean miller_rabin_pass(BigNumber a, BigNumber n) {
	    BigNumber n_minus_one = n.subtract(BigNumber.ONE);
	    BigNumber d = n_minus_one;
		int s = d.getLowestSetBit();
		d = d.shiftRight(s);
	    BigNumber a_to_power = modPow(a,d, n);
	    if (a_to_power.equals(BigNumber.ONE)) return true;
	    for (int i = 0; i < s-1; i++) {
	        if (a_to_power.equals(n_minus_one)) return true;
	        a_to_power = a_to_power.multiply(a_to_power).mod(n);
	    }
	    if (a_to_power.equals(n_minus_one)) return true;
	    return false;
	}

	public boolean miller_rabin(BigNumber n) {
	    for (int repeat = 0; repeat < 20; repeat++) {
	        BigNumber a;
	        do {
	            a = new BigNumber(n.bitLength(), new Random());
	        } while (a.equals(BigNumber.ZERO));
	        if (!miller_rabin_pass(a, n)) {
	            return false;
	        }
	    }
	    return true;
	}
	
    public BigNumber modInverse(BigNumber a, BigNumber m) {
        BigNumber modVal = a;
        if (a.signum < 0 || (a.compareMagnitude(m) >= 0))
            modVal = a.mod(m);

        if (modVal.equals(1))
            return BigNumber.ONE;

        MutableBigNumber c = new MutableBigNumber(modVal);
        MutableBigNumber b = new MutableBigNumber(m);

        MutableBigNumber result = c.mutableModInverse(b);
        return result.toBigNumber(1);
    }
}
