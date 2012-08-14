xmlvalidator
============

Validação de arquivo XML
Na classe principal "Janel Principal.java" para cada aba de arquivo xml aberta é adicionado em uma HashTable com <pre>HashTable &lt;nome_tabela.xml, TabelaXML&gt;</pre> e criado um objeto TabelaXML.
O objeto TabelaXML instancia ValidacaoEstrutural passando como parâmetro "nome_tabela.xml", o qual remove o .xml e busca a tabela pelo DAO filtrando o ultimo layout.