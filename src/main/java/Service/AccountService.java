package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        if(verifyLogin(account) == null){
            return accountDAO.addAccount(account.getUsername(), account.getPassword());
        }
        return null;
    }

    public Account verifyLogin(Account account){
        return accountDAO.verifyLogin(account.getUsername(), account.getPassword());
    }

    public Account getAccountById(int account_id){
        return accountDAO.getAccountById(account_id);
    }
    
}
