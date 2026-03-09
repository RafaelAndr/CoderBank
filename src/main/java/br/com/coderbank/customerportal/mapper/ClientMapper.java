package br.com.coderbank.customerportal.mapper;

import br.com.coderbank.customerportal.dto.request.ClientRequestDto;
import br.com.coderbank.customerportal.dto.response.ClientListDto;
import br.com.coderbank.customerportal.dto.response.ClientResponseDto;
import br.com.coderbank.customerportal.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientRequestDto dto);

    ClientResponseDto toDto(Client client);

    ClientListDto toPagedDto(Client client);
}
