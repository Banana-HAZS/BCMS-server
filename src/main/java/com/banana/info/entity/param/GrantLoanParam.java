package com.banana.info.entity.param;

import com.banana.info.entity.Loan;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2024-03-29
 */
@Data
public class GrantLoanParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    public Loan toLoan(){
        Loan loan = new Loan();
        loan.setId(id);
        loan.setGrantDate(LocalDateTime.now());

        return loan;
    }
}
