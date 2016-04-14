var AdminUserPage = React.createClass({
    mixins: [Reflux.connect(UserStore, 'allUsers'), Reflux.connect(RoleStore, 'usersAuthLevel')],
    getInitialState: function () {
        return {allUsers: undefined, usersAuthLevel: undefined};
    },
    componentDidMount: function () {
        UserActions.getAllUsers();
    },
    render: function () {
        var userListNodes = <h1 className="text-center">Loading Users <i className="fa fa-spin fa-refresh"/></h1>;

        if (this.state.allUsers !== undefined && this.state.allUsers.length === 0) {
            userListNodes = <div>No Users Found</div>;
        } else if (this.state.allUsers !== undefined && this.state.allUsers.length > 0 && this.state.usersAuthLevel === undefined) {
            RoleActions.getUsersAuthenticationLevel();
        } else if (this.state.allUsers !== undefined && this.state.allUsers.length > 0) {
            userListNodes = this.state.allUsers.map(function (user) {
                return (<AdminUserLine key={user.email} data={user} authorityLevel={{'USER'}}/>);
            });
        }
        return (
            <div>
                <NavBar />
                <div className="text-center container">
                    <div className="row">
                        <h1>List of all users</h1>
                    </div>
                    <div className="row" style={{height: '700px', overflowY: 'scroll'}}>
                        {userListNodes}
                    </div>
                </div>
            </div>
        );
    }
});
ReactDOM.render(<AdminUserPage />, document.getElementById('admin_user'));