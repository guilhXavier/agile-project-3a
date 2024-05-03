package ifsul.agileproject.rachadinha.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import ifsul.agileproject.rachadinha.domain.dto.RachaDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RachaMapper implements Function<RachaDTO, Racha> {

	@Override
	public Racha apply(RachaDTO dto) {
		return Racha.builder()
				.id(dto.getId())
				.name(dto.getName())
				.description(dto.getDescription())
				.owner(dto.getOwner())
				.status(dto.getStatus())
				.inviteLink(dto.getInviteLink())
				.build();
	}
}