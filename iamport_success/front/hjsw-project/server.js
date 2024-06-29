const express = require('express');
const cors = require('cors');
const app = express();

// CORS 설정
const corsOptions = {
  origin: 'http://localhost:5173', // 클라이언트의 주소를 명시
  credentials: true, // 쿠키 사용을 허용
};

app.use(cors(corsOptions));

app.get('/videoapi/video', (req, res) => {
  res.json({ message: 'This is the video API' });
});

// 기타 라우트 및 설정

app.listen(8080, () => {
  console.log('Server is running on port 8080');
});