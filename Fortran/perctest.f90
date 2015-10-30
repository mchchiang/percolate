module perctest

  implicit none

  private
  public :: perc_test

contains

  !
  ! Function to test for percolation.
  !
  ! Should return 1 if a cluster percolates, 0 otherwise.
  !

  subroutine perc_test(n, percmap, percflag)

    integer, intent(in)                           :: n
    integer, dimension(0:n+1, 0:n+1), intent(in)  :: percmap
    logical, intent(out)                          :: percflag

    percflag = .false.

  end subroutine perc_test

end module perctest
