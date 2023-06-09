package com.senai.eventos;

import com.github.javafaker.Faker;
import com.senai.eventos.models.Empresa;
import com.senai.eventos.models.Endereco;
import com.senai.eventos.models.Evento;
import com.senai.eventos.models.Participacao;
import com.senai.eventos.models.Pessoa;
import com.senai.eventos.models.Publicacao;
import com.senai.eventos.models.Usuario;
import com.senai.eventos.repositories.EmpresaRepository;
import com.senai.eventos.repositories.EventoRepository;
import com.senai.eventos.repositories.ParticipacaoRepository;
import com.senai.eventos.repositories.PessoaRepository;
import com.senai.eventos.repositories.PublicacaoRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

  Faker faker = new Faker();

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private EventoRepository eventoRepository;

  @Autowired
  private PublicacaoRepository publicacaoRepository;

  @Autowired
  private ParticipacaoRepository participacaoRepository;

  @Transactional
  public void run(ApplicationArguments args) {
    if (pessoaRepository.count() > 0){
      return;
    }
    List<Pessoa> pessoas = new ArrayList<>();
    List<Empresa> empresas = new ArrayList<>();
    List<Evento> eventos = new ArrayList<>();
    for (int i = 0; i < 25; i++) {
      pessoas.add(pessoaRepository.save(this.createPessoa()));
      empresas.add(empresaRepository.save(this.createEmpresa()));
      Evento ev = eventoRepository.save(this.createEvento(i % 2 == 0 ? pessoas.get(i) : empresas.get(i)));
      eventos.add(ev);
      publicacaoRepository.save(this.createPublicacao(i % 2 == 0 ? empresas.get(i) : pessoas.get(i), ev));
    }
    for(Evento ev: eventos){
      for(Pessoa ps: pessoas){
        participacaoRepository.save(new Participacao(ev, ps));
      }
    }

  }


  private Publicacao createPublicacao(Usuario us, Evento ev){
    Publicacao pub = new Publicacao();
    pub.setConteudo(faker.lorem().paragraph());
    pub.setPublicador(us);
    pub.setEvento(ev);
    return pub;
  }

  private Pessoa createPessoa() {
    Pessoa pessoa = new Pessoa();
    pessoa.setEmail(faker.name().username().toLowerCase() + "@mail.com");
    pessoa.setNome(faker.name().firstName());
    pessoa.setSenha("12345678");
    var date = faker.date().past(5 * 365, TimeUnit.DAYS);
    pessoa.setDataNascimento(
      this.convertDate(date)
    );
    pessoa.setEndereco(this.createEndereco());
    return pessoa;
  }

  private Empresa createEmpresa() {
    Empresa empresa = new Empresa();
    empresa.setEmail(faker.company().name().toLowerCase().replaceAll(" ", "").replaceAll(",", "") + "@mail.com");
    empresa.setNome(faker.company().name());
    empresa.setSenha(faker.crypto().sha256());
    empresa.setCnpj(String.valueOf(faker.number().randomNumber(14, false)));
    return empresa;
  }

  private Evento createEvento(Usuario us){
    Evento evento = new Evento();
    evento.setDataFim(this.convertDateTime(faker.date().future(30, TimeUnit.DAYS)));
    evento.setDataInicio(this.convertDateTime(faker.date().future(20, TimeUnit.DAYS)));
    evento.setNome(faker.pokemon().name());
    evento.setDescricao(faker.lorem().paragraph());
    evento.setOrganizador(us);
    evento.setLocal(this.createEndereco());
    return evento;
  }

  private Endereco createEndereco(){
    Endereco endereco = new Endereco();
    endereco.setCidade(faker.address().cityName());
    endereco.setEstado(faker.address().stateAbbr());
    endereco.setRua(faker.address().streetName());
    endereco.setNumero(faker.address().buildingNumber());
    endereco.setCep(faker.address().zipCode());
    return endereco;
  }

  private LocalDate convertDate(Date date){
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  private LocalDateTime convertDateTime(Date date){
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
