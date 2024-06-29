<template>
    <div class="imp-container">
      <h1>아임포트 결제</h1>
      <a-form
        :form="form"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
        :colon="false"
        labelAlign="left"
        @submit="handleSubmit"
      >
        <a-form-item label="PG사">
          <a-select
            v-model="pg.data"
            size="large"
            @change="handlePg"
          >
            <a-select-option
              v-bind:key="pg.value"
              v-for="pg in pgs"
              :value="pg.value"
            >
              {{ pg.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="결제수단">
          <a-select
            v-decorator="['payMethod', { initialValue: 'card' }]"
            size="large"
            @change="handleMethod"
          >
            <a-select-option
              v-bind:key="method.value"
              v-for="method in methods"
              :value="method.value"
            >
              {{ method.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="에스크로 결제여부" class="imp-payment-escrow">
          <a-switch v-decorator="['escrow', { valuePropName: 'checked' }]" />
        </a-form-item>
        <a-form-item label="입금기한" v-if="vbankDueVisible">
          <a-input
            placeholder="YYYYMMDDhhmm"
            size="large"
            type="number"
          />
        </a-form-item>
        <a-form-item label="사업자번호" v-if="bizNumVisible">
          <a-input
            v-model="num"
            size="large"
            type="number"
          />
        </a-form-item>
        <a-form-item label="할부개월수" v-if="quotaVisible">
          <a-select
            v-decorator="['quota', { initialValue: '0' }]"
            size="large"
          >
            <a-select-option
              v-bind:key="quota"
              v-for="quota in quotas"
              :value="quota"
            >
              {{ quota === '0' ? 'PG사 기본제공' : quota === '1' ? '일시불' : `${quota}개월` }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="주문번호">
          <a-input
            v-decorator="[
              'merchantUid',
              { initialValue: initialMerchantUid },
            ]"
            size="large"
          />
        </a-form-item>
        <a-form-item label="주문명">
          <a-input
            v-decorator="['name', { initialValue: '아임포트 VueJS 테스트 결제' }]"
            size="large"
          />
        </a-form-item>
        <a-form-item label="결제금액">
          <a-input
            v-decorator="['amount', { initialValue: '1000' }]"
            size="large"
            type="number"
          />
        </a-form-item>
        <a-form-item label="이름">
          <a-input
            v-decorator="['buyerName', { initialValue: '홍길동' }]"
            size="large"
          />
        </a-form-item>
        <a-form-item label="연락처">
          <a-input
            v-decorator="['buyerPhone', { initialValue: '01012341234' }]"
            size="large"
            type="number"
          />
        </a-form-item>
        <a-form-item label="이메일">
          <a-input
            v-decorator="['buyerEmail', { initialValue: 'example@example.com' }]"
            size="large"
          />
        </a-form-item>
        <a-form-item label="주소">
          <a-input
            v-decorator="['buyerAddr', { initialValue: '서울시 강남구 신사동 661-16' }]"
            size="large"
          />
        </a-form-item>
        <a-form-item label="우편번호">
          <a-input
            v-decorator="['buyerPostcode', { initialValue: '06010' }]"
            size="large"
            type="number"
          />
        </a-form-item>
        <a-button size="large" @click="handleGoBack">
          뒤로가기
        </a-button>
        <a-button type="primary" html-type="submit" size="large">
          결제하기
        </a-button>
      </a-form>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import { PGS } from "@/constants";
  import Utils from '@/utils';
  import { Form } from 'ant-design-vue';

  //Test
  const pg = ref('html5_inicis'); // 초기값 설정
  const num = ref(21351351); // 초기값 설정

  // 반응성 상태 변수들
  const formLayout = ref('horizontal');
  const initialMerchantUid = `mid_${new Date().getTime()}`;
  const pgs = PGS;
  let methods = Utils.getMethodsByPg();
  let quotas = Utils.getQuotaByPg();
  const initialMethod = ref('card');
  const vbankDueVisible = ref(false);
  const bizNumVisible = ref(false);
  const quotaVisible = ref(true);
  const form = ref(null);

  // 컴포넌트가 마운트된 후 초기화
  onMounted(() => {
    form.value = Form.useForm()[0]; // Form 객체 생성
  });

  // 이벤트 핸들러들
  const handleSubmit = (e) => {
    e.preventDefault();
    form.value.validateFields((err, values) => {
      if (!err) {
        const {
          pg,
          payMethod,
          escrow,
          vbankDue,
          bizNum,
          quota,
          merchantUid,
          name,
          amount,
          buyerName,
          buyerTel,
          buyerEmail,
          buyerAddr,
          buyerPostcode,
        } = values;
        const { IMP } = window;
        IMP.init(Utils.getUserCodeByPg(pg));
        const data = {
          pg,
          pay_method: payMethod,
          escrow,
          merchant_uid: merchantUid,
          name,
          amount,
          buyer_name: buyerName,
          buyer_tel: buyerTel,
          buyer_email: buyerEmail,
          buyer_addr: buyerAddr,
          buyer_postcode: buyerPostcode,
          niceMobileV2: true,
        };

        if (payMethod === 'vbank') {
          data.vbank_due = vbankDue;
          if (pg === 'danal_tpay') {
            data.biz_num = bizNum;
          }
        }
        if (payMethod === 'card') {
          data.display = {
            card_quota: quota,
          };
        }
        IMP.request_pay(data, callback);
      }
    });
  };

  const handlePg = (pg) => {
    methods = Utils.getMethodsByPg(pg);
    const [{ value }] = methods;
    form.value.setFieldsValue({
      payMethod: value,
    });
    setVisible(pg, value);
  };

  const handleMethod = (method) => {
    const pg = form.value.getFieldValue('pg');
    setVisible(pg, method);
  };

  const setVisible = (pg, method) => {
    vbankDueVisible.value = method === 'vbank';
    bizNumVisible.value = pg === 'danal_tpay' && vbankDueVisible.value;
    quotaVisible.value = method === 'card';
  };

  const handleGoBack = () => {
    $router.push('/');
  };

  const callback = (response) => {
    const query = {
      ...response,
      type: 'payment',
    };
    $router.push({ path: '/result', query });
  };

</script>


  
  <!-- Style 시작 -->

<style scoped>
  .imp-container {
    width: 80%;
    margin: 0 auto;
    background-color: #fff;
    border-radius: 3px;
    padding: 2rem 5rem;
  }
  
  h1 {
    margin-bottom: 50px;
  }
  
  .imp-payment-escrow .ant-form-item-control {
    text-align: left;
  }
  
  .ant-btn {
    margin: 20px 5px 0 5px;
  }
  
  .ant-row.ant-form-item {
    margin-bottom: 10px;
  }
  </style>
  