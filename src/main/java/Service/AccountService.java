package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDao;

    public AccountService(){
        accountDao = new AccountDAO();
    }

    public AccountService(AccountDAO accountDao){
        this.accountDao=accountDao;
    }

    public Account insertAccount(Account account){
        Account result = accountDao.insertAccount(account);
        return result;

    }

    public Account loginAccount(Account account){
        Account result = accountDao.loginAccount(account);
        return result;
    }    
}