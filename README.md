java-signup
======

java-signup is a module which allows you to execute basic operations on user's account. It allows:
* singup
* signin
* reset password
* delete account
* update account
* send email with new password
* send email with welcome message


## Hello world:
**pom.xml**
```
  ...
  <dependency>
    <groupId>com.github.java-signup</groupId>
    <artifactId>java-signup</artifactId>
    <version>1.0</version>
  </dependency>
  ...
```

**Main.java**
```
// register new user
AccountDAO accountDAO = ...; // your DAO here
AccountService accountService = new SimpleAccountService(accountDAO);
accountService.register(new Account("johny", "secret"));

// authenticate
System.out.println(accountService.authenticate("johny", "secret")); // true
System.out.println(accountService.authenticate("johny", "badpass")); // false
```

## How to delete an account
```
accountService.register(new Account("user1", "secret"));
accountService.register(new Account("user2", "secret"));
accountService.delete("user1");
System.out.println(accountService.authenticate("user1", "secret")); // false
System.out.println(accountService.authenticate("user2", "secret")); // true
```

## How to update an account
```
accountService.register(new Account("johny", "secret"));
accountService.update(new Account("johny", "bettersecret"));
System.out.println(accountService.authenticate("johny", "secret")); // false
System.out.println(accountService.authenticate("johny", "bettersecret")); // true
```

## Configuration in details
(1) Add dependency to pom.xml (see Hello World example in the top)

(2) You need to implement your own DAO. It can be simple, in memory, just to test. For instance:

**InMemoryAccountDAO.java**

```
package signup.client;

import com.github.signup.domain.Account;
import com.github.signup.domain.dao.AccountDAO;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountDAO implements AccountDAO {

    private Map<String, Account> memory = new HashMap<String, Account>();

    @Override
    public boolean exists(String s) {
        return memory.get(s) != null;
    }

    @Override
    public boolean exists(String s, String s1) {
        return memory.get(s) != null && memory.get(s).getPassword().equals(s1);
    }

    @Override
    public void add(Account account) {
        memory.put(account.getUsername(), account);
    }

    @Override
    public void remove(String s) {
        memory.remove(s);
    }

    @Override
    public void update(Account account) {
        memory.put(account.getUsername(), account);
    }

    @Override
    public Account load(String s) {
        return memory.get(s);
    }
}
```

(3) That's it. Just use SimpleAccountService implementation

