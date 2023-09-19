package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDao;

    public AccountService(){
        accountDao = new AccountDAO();
    }

    public AccountService(AccountDAO accountDao){
        this.accountDao = accountDao;
    }

    public void registerAccount(Account account) {
        accountDao.register(account);
    }

    public Account findbyusername(String username) {
        return accountDao.findbyusername(username);
    }

    public Account findbyusernameAndpwd(Account account) {
        return accountDao.findbyusernameAndpwd(account);
    }
}