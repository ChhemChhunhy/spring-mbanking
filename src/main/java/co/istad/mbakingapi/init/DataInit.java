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
        // auto generated
        if (accountTypeRepository.count()<1){
           AccountType payRoll = new AccountType();
           payRoll.setName("Payroll");
           payRoll.setAlias("payroll");
           payRoll.setIsDeleted(false);
           payRoll.setDescription("For payroll account type");

           AccountType saving = new AccountType();
           saving.setName("Saving");
           saving.setAlias("saving");
           saving.setIsDeleted(false);
           saving.setDescription("For saving account type");

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
