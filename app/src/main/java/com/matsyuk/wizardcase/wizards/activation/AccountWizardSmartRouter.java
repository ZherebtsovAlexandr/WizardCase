package com.matsyuk.wizardcase.wizards.activation;

import com.matsyuk.wizardcase.presentation.info.wizard_part.InfoWizardPart;
import com.matsyuk.wizardcase.presentation.login.wizard_part.LoginWizardPart;
import com.matsyuk.wizardcase.presentation.registration.wizard_part.RegistrationWizardPart;

import ru.terrakok.cicerone.Router;

import static com.matsyuk.wizardcase.wizards.RouterConstants.*;
import static com.matsyuk.wizardcase.wizards.activation.AccountWizardStep.*;

/**
 * @author e.matsyuk
 */
public class AccountWizardSmartRouter {

    private final Router router;
    private final AccountWizardResult accountWizardResult;
    private AccountWizardStep accountWizardStep = NONE;

    private final InfoWizardPart infoWizardPart = new InfoWizardPart() {

        @Override
        public void infoWizardNext() {
            accountWizardStep = LOGIN;
            router.navigateTo(LOGIN_SCREEN);
        }

        @Override
        public void infoWizardBack() {
            accountWizardStep = NONE;
            router.finishChain();
        }

    };

    private final LoginWizardPart loginWizardPart = new LoginWizardPart() {

        @Override
        public void accountLoginWizardSuccess() {
            accountWizardStep = NONE;
            router.finishChain();
            accountWizardResult.onSuccess();
        }

        @Override
        public void accountLoginWizardBack() {
            accountWizardStep = NONE;
            router.finishChain();
            accountWizardResult.onBack();
        }

        @Override
        public void accountLoginWizardNewAccount() {
            accountWizardStep = REGISTRATION;
            router.navigateTo(REGISTRATION_SCREEN);
        }

    };

    private final RegistrationWizardPart registrationWizardPart = new RegistrationWizardPart() {

        @Override
        public void accountRegistrationWizardSuccess() {
            accountWizardStep = NONE;
            router.finishChain();
            accountWizardResult.onSuccess();
        }

        @Override
        public void accountRegistrationWizardBack() {
            accountWizardStep = LOGIN;
            router.backTo(LOGIN_SCREEN);
        }

    };

    public AccountWizardSmartRouter(Router router,
                                    AccountWizardResult accountWizardResult) {
        this.router = router;
        this.accountWizardResult = accountWizardResult;
    }

    public void startWizard() {
        if (accountWizardStep != NONE) {
            return;
        }
        accountWizardStep = INFO;
        router.navigateTo(INFO_ACCOUNT_SCREEN);
    }

    public InfoWizardPart getInfoWizardPart() {
        return infoWizardPart;
    }

    public LoginWizardPart getLoginWizardPart() {
        return loginWizardPart;
    }

    public RegistrationWizardPart getRegistrationWizardPart() {
        return registrationWizardPart;
    }

}