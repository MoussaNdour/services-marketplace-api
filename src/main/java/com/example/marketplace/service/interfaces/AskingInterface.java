package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.AskingRequestDTO;
import com.example.marketplace.dto.request.AskingUpdateDTO;
import com.example.marketplace.dto.response.AskingResponseDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.User;

import java.util.List;

public interface AskingInterface extends GeneralInterface<AskingRequestDTO, AskingResponseDTO>{

    List<AskingResponseDTO> getClientAskings(User user);
    ClientResponseDTO getClientByAskingId(int id);
    ServiceProposalResponseDTO getProposalByAskingId(int id);
    AskingResponseDTO updateStatus(int id, AskingUpdateDTO dto);
}
