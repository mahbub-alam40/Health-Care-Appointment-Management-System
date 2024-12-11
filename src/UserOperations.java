public interface UserOperations {
    void register(String username, String password, String name, String email);
    boolean login(String username, String password) throws Exception;
}
