package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private static AccountDAO accountDao;

    public AccountService(){
        accountDao = new AccountDAO();
    }

    public AccountService(AccountDAO accountDao){
        AccountService.accountDao = accountDao;
    }

    public void registerAccount(Account account) {
        accountDao.register(account);
    }

    public static Account findbyusername(String username) {
        return accountDao.findbyusername(username);
    }

    public Account findbyusernameAndpwd(String username, String password) {
        return accountDao.findbyusernameAndpwd(username, password);
    }
}