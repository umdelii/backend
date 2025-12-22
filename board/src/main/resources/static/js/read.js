const url = `http://localhost:8080/replies`;
const replyList = document.querySelector(".reply-list");

// 날짜/시간 이쁘게 띄우기
const formatDate = (data) => {
  const date = new Date(data);
  // 2025-12-16 15:26
  return (
    date.getFullYear() +
    "-" +
    (date.getMonth() + 1) +
    "-" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 댓글 리스트 띄우기
const getReplyList = () => {
  fetch(`${url}/board/${bno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      console.log(data);

      // 댓글 개수 보여주기
      // document
      //   .querySelector(".current-replies")
      //   .insertAdjacentText("afterbegin", data.length + " replies");

      replyList.previousElementSibling.firstElementChild.innerText =
        data.length + " replies";

      let result = "";

      data.forEach((reply) => {
        result += `          <div class="d-flex justify-content-between my-2 border-bottom" data-rno="${
          reply.rno
        }" data-email="${reply.replyerEmail}">
            <div class="p-3">
              <img
                src="/img/userIcon.png"
                alt=""
                class="rounded-circle mx-auto d-block"
                style="width: 60px; height: 60px"
              />
            </div>
            <div class="flex-grow-1 align-self-center">
              <div>${reply.replyerName}</div>
              <div>
                <span class="fs-5">${reply.text}</span>
              </div>
              <div class="text-muted">
                <span class="small">${formatDate(reply.createDateTime)}</span>
              </div>
            </div>
            <div class="d-flex flex-column align-self-center">
            <div class="mb-2">
                <button class="btn btn-outline-success btn-sm">Update</button>
                </div>
                <div class="mb-2">
                <button class="btn btn-outline-danger btn-sm">Delete</button>
              </div>
            </div>
          </div>
          `;
      });
      replyList.innerHTML = result;
    })
    .catch((err) => console.log(err));
};

// 함수에 담았으니 불러야 화면에 뜸
getReplyList();

// 댓글 추가(post)하기
// 작성 클릭 시 == submit 발생
// submit 막고
// json 형태로 데이터 변환 후 보내기
// post요청들어가기
document.querySelector("#reply-form").addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const rno = form.rno.value;
  const reply = {
    rno: rno,
    text: form.text.value,
    replyerEmail: form.replyerEmail.value,
    bno: bno,
  };

  // 수정 버튼과 추가 버튼이 같으니 rno의 value 존재여부로 판단하기
  if (!rno) {
    //new
    fetch(`${url}/new`, {
      method: "POST",
      headers: {
        "X-CSRF-TOKEN": csrfVal,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reply),
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`에러 발생${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "댓글 추가 완료",
            icon: "success",
            draggable: true,
          });
        }

        // form.replyer.value = "";
        form.text.value = "";

        getReplyList();
      })
      .catch((err) => console.log(err));
  } else {
    // modify
    const replyForm = document.querySelector("#reply-form");
    fetch(`${url}/${rno}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(reply),
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`에러 발생${res.status}`);
        }
        return res.text();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "댓글 수정 완료",
            icon: "success",
            draggable: true,
          });
        }
        replyForm.replyer.removeAttribute("readonly");
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";
        replyForm.rbtn.innerHTML = "Insert Reply";

        getReplyList();
      })
      .catch();
  }
});
// 삭제(수정) 버튼 누르면 rno 값 화면에 찍게하기

// 내가 만들다만 코드(망함ㅎ)
// let getIndex = data.indexOf();
// let deleteButton = `.reply-list:nth-child(${getIndex})}>.btn-outline-danger)`;
// document.querySelector(deleteButton).addEventListener("click", (e) => {
//   console.log(e.target.dataset.rno);
// });

// 강사님 코드
// 방법 1 (삭제따로 수정따로 코드를 짜야함)
// document.querySelectorAll(".btn-outline-danger").forEach((btn) => {
//   btn.addEventListener("click", (e) => {
//     const targetBtn = e.target;
//     const rno = targetBtn.closest(".border-bottom").dataset.rno;
//   });
// });

// 방법2 (이벤트 버블링)
replyList.addEventListener("click", (e) => {
  const btn = e.target;
  console.log(btn);

  // 부모쪽으로만 검색
  const rno = btn.closest(".border-bottom").dataset.rno;
  console.log("rno:{}", rno);

  // 삭제인지 수정인지 확인
  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("삭제하시겠습니까?")) return;

    // true 인 경우 삭제(fetch)
    fetch(`${url}/${rno}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`에러 발생${res.status}`);
        }
        return res.text();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "데이터 삭제 완료",
            icon: "success",
            draggable: true,
          });
        }
        // 댓글 다시 가져오기(새로고침)
        getReplyList();
      })
      .catch((err) => console.log(err));
  } else if (btn.classList.contains("btn-outline-success")) {
    // rno를 이용해 reply 가져오기
    // 가져온 reply를 폼에 띄우기
    // 댓글 작성 버튼 => 수정 버튼으로 text바꾸기
    const replyForm = document.querySelector("#reply-form");
    fetch(`${url}/${rno}`)
      .then((res) => {
        if (!res.ok) {
          throw new Error(`에러 발생${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        console.log(data);
        replyForm.rno.value = data.rno;
        replyForm.replyer.value = data.replyer;
        // replyForm.replyer.readOnly = true;
        replyForm.replyer.setAttribute("readonly", "true");
        replyForm.text.value = data.text;
        replyForm.rbtn.innerHTML = "Update Reply";
      })
      .catch();
  }
});
