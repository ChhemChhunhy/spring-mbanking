package co.istad.mbakingapi.init;

import co.istad.mbakingapi.domain.AccountType;
import co.istad.mbakingapi.domain.CardType;
import co.istad.mbakingapi.domain.Role;
import co.istad.mbakingapi.features.accountType.AccountTypeRepository;
import co.istad.mbakingapi.features.accountType.dto.AccountTypeResponse;
import co.istad.mbakingapi.features.cardType.CardTypeRepository;
import co.istad.mbakingapi.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final CardTypeRepository cardTypeRepository;
    @PostConstruct
    void initRole(){
        // auto generated
        if (roleRepository.count()<1){
            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role staff = new Role();
            staff.setName("STAFF");

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.saveAll(
                    List.of(user,customer,staff,admin)
            );
        }

    }

    @PostConstruct
    void initAccountType(){
        if (accountTypeRepository.count()<1){
           AccountType payRoll = new AccountType();
           payRoll.setName("Payroll Account");
           payRoll.setAlias("payroll-account");
           payRoll.setIsDeleted(false);
           payRoll.setDescription("A payroll account is a checking account that a business uses to pay its employees.");

           AccountType saving = new AccountType();
           saving.setName("Saving Account");
           saving.setAlias("saving-account");
           saving.setIsDeleted(false);
           saving.setDescription("A savings account is a type of bank account that allows you to store money and earn interest.");

            AccountType card = new AccountType();
            card.setName("Card Account");
            card.setAlias("card-account");
            card.setIsDeleted(false);
            card.setDescription("A card account is the account that a cardholder has with a bank, and where withdrawals are debited and deposits are credited when the cardholder makes a transaction. ");
            accountTypeRepository.save(card);

            accountTypeRepository.saveAll(
                   List.of(payRoll,saving)
           );


        }

    }

    @PostConstruct
    void initCardType(){
        // auto generated
        if (cardTypeRepository.count()<1){
            CardType visa = new CardType();
            visa.setName("Visa");
            visa.setIsDeleted(false);

            CardType masterCard = new CardType();
            masterCard.setName("MasterCard");
            visa.setIsDeleted(false);

            cardTypeRepository.saveAll(
                    List.of(visa,masterCard)
            );

        }

    }
}
