package com.ingsoft.tf.api_edurents.repository.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    List<Transaction> findByProducto_Vendedor_Id(Integer idSeller);
    List<Transaction> findByProducto_Vendedor_IdAndEstado(Integer idSeller, TransactionStatus estado);
    List<Transaction> findByProducto_Vendedor_IdAndMetodoPago(Integer idSeller, PaymentMethod metodo);
    List<Transaction> findByProducto_Vendedor_IdAndMetodoPagoAndEstado(Integer idSeller, PaymentMethod metodo, TransactionStatus estado);


}
