package com.habosa.javasnap;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class StoryEncryption {
		private final Cipher cipher;

		private StoryEncryption(Cipher cipher) {
			this.cipher = cipher;
		}

		public static StoryEncryption create() throws EncryptionException {
			try {
				return new StoryEncryption(Cipher.getInstance("AES/CBC/PKCS5Padding"));
			} catch (NoSuchPaddingException | NoSuchAlgorithmException | NoSuchProviderException e) {
				throw new EncryptionException(e);
			}
		}

    public byte[] decrypt(byte[] storyData, String mediaKey, String mediaIV) throws EncryptionException {
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.decodeBase64(mediaIV.getBytes()));
        SecretKeySpec keySpec = new SecretKeySpec(Base64.decodeBase64(mediaKey.getBytes()), "AES");
        
        try {
          cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
          return cipher.doFinal(storyData);
        } catch (Exception e) {
					throw new EncryptionException(e);
        }
    }
}
