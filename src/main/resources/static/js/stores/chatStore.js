var ChatStore = Reflux.createStore({
    listenables: [ChatActions],
    init : function() {
      this.state = {};
    },
    onGetChats: function() {
        $.ajax({
            url: '/api/chat/list',
            dataType: 'json',
            success : this.triggerUpdate
        });
    },
    onHandleResponse(chatUserId, response) {

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/api/chat/' + chatUserId + '/respond',
            data: response,
            type: 'POST',
            success: function() {
                window.location.reload(true);
            }
        });
    },
    onCreateGroup(groupName) {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url : '/api/chat/group/create',
            data: groupName,
            type: 'POST',
            success : function() {
                window.location.reload(true);
            }
        });
    },
    triggerUpdate : function(data) {
        this.trigger(data);
    },
    onGetChatGroup : function() {
        var id = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        $.ajax({
            url: '/api/chat/group/' + id,
            dataType: 'json',
            success : this.updateState
        })
    },
    onSendMessage : function(message) {
        var id = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/api/chat/group/' + id + '/message',
            data : message,
            type : 'POST',
            dataType: 'json',
            success : this.updateMessageState
        })
    },
    updateState : function(data) {
        this.state = data;
        this.triggerUpdate(this.state);
    },
    updateMessageState : function(message) {
        this.state.chatMessages.push(message);
        this.triggerUpdate(this.state);
    },
    updateUserState : function(user) {
        this.state.chatUsers.push(user);
        this.triggerUpdate(this.state);
    },
    updateChatName : function(chatName) {
      this.state.chatName = chatName;
        this.triggerUpdate(this.state);
    },
    onEditGroupName : function(newName) {
        var id = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url : '/api/chat/group/'+ id +'/updateName',
            type: 'POST',
            data: newName,
            dataType: 'text',
            success: this.updateChatName
        });
    },
    onInviteConnection : function(userId) {
        var id = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url : '/api/chat/group/' + id +"/invite",
            type : 'POST',
            data: JSON.stringify(userId),
            dataType: 'json',
            success : this.updateUserState
        });
    }
});