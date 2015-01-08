package com.habosa.javasnap;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Encryption {
    private static final SecretKeySpec secretKeySpec = new SecretKeySpec("M02cnQ51Ji97vwT4".getBytes(), "AES");
		private final Cipher cipher;

		private static enum CryptMode {
			ENCRYPT(Cipher.ENCRYPT_MODE),
			DECRYPT(Cipher.DECRYPT_MODE);
			private final int mode;

			int mode() {
				return this.mode;
			}
			CryptMode(int mode) {
				this.mode = mode;
			}
		}

		private Encryption(Cipher cipher) {
			this.cipher = cipher;
		}

		public static Encryption create() throws EncryptionException {
    	final String CIPHER_MODE = "AES/ECB/PKCS5Padding";
      Cipher cipher;
      try {
          cipher = Cipher.getInstance(CIPHER_MODE, "BC");
      } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
          throw new EncryptionException(e);
      } catch (NoSuchProviderException none) {
          try{
            cipher = Cipher.getInstance(CIPHER_MODE);
          }
          catch(Exception e){
            throw new EncryptionException(e);
          }
      }
			return new Encryption(cipher);
		}

    public byte[] encrypt(byte[] data) throws EncryptionException {
			return crypt(CryptMode.ENCRYPT, data);
    }

    public byte[] decrypt(byte[] data) throws EncryptionException {
			return crypt(CryptMode.DECRYPT, data);
    }

		private byte[] crypt(CryptMode mode, byte[] data) throws EncryptionException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(data);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptionException(e);
        }
		}
}
