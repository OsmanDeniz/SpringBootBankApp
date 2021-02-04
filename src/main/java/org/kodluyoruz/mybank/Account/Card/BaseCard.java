package org.kodluyoruz.mybank.Account.Card;


import org.hibernate.annotations.GenericGenerator;
import org.kodluyoruz.mybank.Account.MoneyManagement.Shopping.Shopping;

import javax.persistence.*;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseCard {

    @ManyToMany(mappedBy = "cardId", targetEntity = Shopping.class)
    protected Set<Shopping> shoppingId;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String cardNumber;

}
