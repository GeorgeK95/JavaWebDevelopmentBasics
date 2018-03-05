package bookhut.services;

import bookhut.entities.User;
import bookhut.models.bindingModels.LoginBindingModel;
import bookhut.repositories.contracts.IUserRepository;
import bookhut.repositories.factories.AbstractRepoFactory;
import bookhut.repositories.factories.FactoryProducer;
import bookhut.services.contracts.IUserService;
import bookhut.utils.DTOConverter;

import static bookhut.utils.Constants.USER;

/**
 * Created by George-Lenovo on 02/15/2018.
 */
public class UserService implements IUserService {
    private IUserRepository userRepository;

    public UserService() {
        AbstractRepoFactory userFactory = FactoryProducer.getFactory(USER);
        this.userRepository = userFactory.getUserRepository();
    }

    @Override
    public void createUser(LoginBindingModel loginModel) {
        User user = DTOConverter.convert(loginModel, User.class);
        this.userRepository.createUser(user);
    }

    @Override
    public LoginBindingModel findUser(String username, String password) {
        User foundUser = this.userRepository.findByUsernameAndPassword(username, password);
        LoginBindingModel loginBindingModel = null;
        if (foundUser != null) {
            loginBindingModel = DTOConverter.convert(foundUser, LoginBindingModel.class);
        }

        return loginBindingModel;
    }

    @Override
    public boolean exist(String username) {
        return this.userRepository.exist(username);
    }

}
