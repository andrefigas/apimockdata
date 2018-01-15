package wiki.kotlin.mockapi.models;


public class Credentials {
    public String mLogin;
    public String mPassword;

    public Credentials(String login, String password) {
        mLogin = login;
        mPassword = generateHash(password);
    }

    private static String generateHash(String password) {
        String md5 = null;
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(), 0, password.length());
            md5 = new java.math.BigInteger(1, digest.digest()).toString(16);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

    public String getLogin() {
        return mLogin;
    }

    public String getPassword() {
        return mPassword;
    }
}
