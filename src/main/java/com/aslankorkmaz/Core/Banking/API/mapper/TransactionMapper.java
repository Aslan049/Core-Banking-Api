package com.aslankorkmaz.Core.Banking.API.mapper;

import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransactionResponse;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransferResponse;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toDtoTransaction(Transaction transaction);

    TransferResponse toDtoTransfer(Transaction transaction);
}
