import Footer from "../components/Footer"
import Navbar from "../components/Navbar"
import "../styles/Informacoes.css"

export function Informacoes({quantTotal, cart}) {
    return (
        <div>
            <Navbar quantTotal={quantTotal} cart={cart} />
            <div className="info-body">
                <h1 className="page-body-title">Informações</h1>
                <h2 className="page-body-subtitle">Troca e Devoluções</h2>
                <p className="page-body-text">
                    Todos os pedidos de troca e devolução devem ser requisitados pelo email <a href="mailto::contato@cdist.com">contato@cdist.com</a> ou
                    pelo número de telefone <b>(46) 99999-9999</b> dentro de um prazo de 15 dias.
                </p>
                <p className="page-body-text">
                    Para fazer uma devolução ou troca, solicite passando a nota fiscal enviada por email com o número do seu pedido e indicando o motivo
                    pelo qual deseja solicitar.<br />Em caso de troca os custos logísticos serão custeados por nossa parte, e uma nova data de envio com as
                    informações de rastreio será enviada por email ao comprador, após o pedido original ter sido recebido por nós.<br />Em casos de
                    devolução, os custo logísticos são custeados por nossa parte e o reembolos será feito de volta para a conta de pagamento, com a mesma
                    modalidade em que foi realizado o pagamento. Caso o valor tenha sido por boleto, solicitaremos conta corrente para depósito ou a opção
                    de um vale-compras em nossa loja, à escolha do cliente.
                </p>
                <h2 className="page-body-subtitle">Frete e Entrega</h2>
                <p className="page-body-text">
                    Na página de cada produto existe um formulário para calcular prazo e valor de entrega de uma unidade do item. Porém a cada unidade de
                    item adicionada ao carrinho, o valor e o prazo do frete pode sofrer alterações de acordo com os produtos.<br />O prazo de entrega
                    começa a ser contabilizado a partir da data de aprovação do pagamento, e é enviado para todo o Brasil.
                </p>
                <h2 className="page-body-subtitle">FAQ</h2>
                <p className="page-body-text">
                    <b>Q: Minha entrega atrasou! O que posso fazer?</b><br />A: Isto pode ser por conta de uma ou mais coisas como: Erro no endereço;
                    condições de clima; erro no pagamento; inspeções de rotina; e adversidades técnicas. Caso tenha sido fruto de erro, será enviado um
                    email diretamente ao comprador para resolução, caso algum imprevisto ocorra, iremos estimar uma nova data de entrega.
                </p>
                <p className="page-body-text">
                    <b>Q: Como faço para trocar ou devolver um produto?</b><br />A: Envie um email solicitando a troca ou devolução do pedido via nosso
                    email ou telefone de contato (<a href="mailto::contato@cdist.com">contato@cdist.com</a> ou (46) 99999-9999), escreva sua situação e
                    detalhe para nós o que deseja. Anexe ao email a nota fiscal do pedido, caso o pedido possua mais de um item, mas a troca/devolução
                    seja apenas de um, declare isto na sua mensgaem. Caso seja enviado em até 15 dias após o recebimento do produto, nós processaremos e
                    instruiremos os próximos passos via email ou telefone.
                </p>
                <p className="page-body-text">
                    <b>Q: Como faço para cancelar uma compra?</b><br />A: Envie um email solicitando o cancelamento do pedido via nosso email ou telefone
                    de contato (<a href="mailto::contato@cdist.com">contato@cdist.com</a> ou (46) 99999-9999). Anexe ao email a nota fiscal do pedido,
                    para então processarmos o requerimento e passaremos a etapa de reembolos que será detalhada com mais informações na resposta que
                    enviaremos.
                </p>
                <h2 className="page-body-subtitle">Termos de Uso</h2>
                <p className="page-body-text">
                    Os termos a seguir servem pra regular como o uso da plataforma ocorre.<br />Ao criar uma conta e fazer uso deste website, você
                    automaticamente concorda com tudo aqui descrito. Em caso de dúvidas, confira a seção <b>FAQ</b>, caso ainda não tenha sido sanada, nos
                    contate pelo email ou telefone (<a href="mailto::contato@cdist.com">contato@cdist.com</a> ou (46) 99999-9999).
                </p>
                <p className="page-body-text">
                    Para realizar cadastro na loja, é necessário utilizar um email existente e uma senha, salve estes dados para login posterior.<br />O
                    usuário é responsável pela guarda e sigilo do seu login, não nós responsabilizamos por informar seus dados para terceiros.<br />É
                    proibido qualquer uso da plataforma para ações criminosas, ou acesso da página via bots, sujeito a banimento permanente de sua
                    conta.<br />Ao utilizar a plataforma nós garantimos estar comprometidos com a sua privacidade.<br />
                </p>
                <h2 className="page-body-subtitle">Política de Privacidade</h2>
                <p className="page-body-text">
                    Nós da CDist se comprometemos a manter a privacidade e integridade de seus dados. Coletamos estes para que possam gerar um serviço
                    melhor para você. Apesar disso os seus dados podem ser acessados, verificados, removidos e corrigidos conforme seu requerimento.
                </p>
            </div>
            <Footer />
        </div>
    )
}