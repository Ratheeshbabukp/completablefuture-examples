import java.util.stream.Collectors;
import java.util.concurrent.*;
import java.util.*;
public class CFDemo {

    public static void main(String[] args) {    
        CFDemo cfDemo = new CFDemo();    
        try {
            // blocking call
            cfDemo.getUsers();
        } catch (ExecutionException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void getUsers() throws ExecutionException, InterruptedException{
        CompletableFuture<List<User>> userList = CompletableFuture.supplyAsync(() -> {
            return getListOfUsers();
        }).thenCompose(users-> {            
                List<User> upperCaseList = null;
                try {
                    upperCaseList = users.get().stream().map(
                                user->{
                                    user.setFirstName(user.getFirstName().toUpperCase());
                                    user.setLastName(user.getLastName().toUpperCase());
                                    return user;
                                }).collect(Collectors.toList());
                } catch (InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            return CompletableFuture.completedFuture(upperCaseList);
        });
        
        userList.get().forEach(System.out::println);
    }
    
    // Dummy method for adding List of Users
    private CompletableFuture<List<User>> getListOfUsers() {
        List<User> users = new ArrayList<User>();
        users.add(new User("Jack", "Reacher", "abc@xyz.com"));    
        users.add(new User("Remington", "Steele", "rs@cbd.com"));
        users.add(new User("Laura", "Holt", "lh@cbd.com"));
        users.add(new User("Jonathan", "Raven", "jr@sn.com"));
        users.add(new User("Tom", "Hanson", "th@jd.com"));
        users.add(new User("Alexander", "Scott", "as@is.com"));
        users.add(new User("Jim", "Phelps", "jp@mi.com"));
        return CompletableFuture.completedFuture(users);
    }
}

