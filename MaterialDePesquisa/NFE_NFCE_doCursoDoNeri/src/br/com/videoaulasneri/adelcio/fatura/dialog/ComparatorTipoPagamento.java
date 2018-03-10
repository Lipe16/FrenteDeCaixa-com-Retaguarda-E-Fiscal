/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura.dialog;

import br.com.videoaulasneri.adelcio.fatura.bean.TotalTipoPagamentoVO;
import java.util.Comparator;

public class ComparatorTipoPagamento implements Comparator<TotalTipoPagamentoVO>{

    @Override
    public int compare(TotalTipoPagamentoVO o1, TotalTipoPagamentoVO o2) {
        return o1.getTipoPagamentoVO().getId() < o2.getTipoPagamentoVO().getId() ? -1 : (o1.getTipoPagamentoVO().getId() > o2.getTipoPagamentoVO().getId() ? 1 : 0);
    }
}
